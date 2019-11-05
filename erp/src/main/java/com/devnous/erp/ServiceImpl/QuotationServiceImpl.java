package com.devnous.erp.ServiceImpl;

import com.devnous.erp.Entity.*;
import com.devnous.erp.Exceptions.NoContentException;
import com.devnous.erp.Exceptions.ResourceNotFoundException;
import com.devnous.erp.Exceptions.UniqueAttributeException;
import com.devnous.erp.Repository.OrderRepository;
import com.devnous.erp.Repository.QuotationRepository;
import com.devnous.erp.Service.HelperFolioService;
import com.devnous.erp.Service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("quotationService")
public class QuotationServiceImpl implements QuotationService {
    @Autowired
    @Qualifier("quotationRepository")
    QuotationRepository quotationRepository;
    private String clase = Quotation.class.getSimpleName();

    @Autowired
    @Qualifier("helperFolioService")
    HelperFolioService helperFolioService;

    @Autowired
    @Qualifier("orderRepository")
    OrderRepository orderRepository;

    @Override
    @SuppressWarnings("Duplicates")
    public void createQuotation(Quotation quotation) {
        if (quotation.getFolio() == null || quotation.getFolio().isEmpty()) {
            String lastFolio = ""; //we need to obtain the last folio of a series.
            try {
                lastFolio = quotationRepository.findTopBySeries(quotation.getSeries()).getFolio();
            } catch (Exception e) {
                //nothing to do
                System.out.println(e);
            }
            quotation.setFolio(helperFolioService.createNewFolio(lastFolio));
        }
        Quotation exist = quotationRepository.findByFolioAndSeries(quotation.getFolio(), quotation.getSeries());
        if (exist == null) {
            double total = 0;
            for (QuotationDetail quotationDetail : quotation.getQuotationDetails()) {
                quotationDetail.setTotalPrice(quotationDetail.getUnitPrice() * quotationDetail.getQuantity());
                total += quotationDetail.getTotalPrice();
            }
            quotation.setTotalPrice(total);
            quotation.setDate(new Date(System.currentTimeMillis()));
            quotation.setStatus(1);
            quotationRepository.save(quotation);
        } else {
            throw new UniqueAttributeException("[series,folios,idCompany]");
        }
    }

    @Override
    public Quotation readQuotation(int id) {
        Quotation quotation = quotationRepository.findById(id);
        if (quotation == null) {
            throw new ResourceNotFoundException(clase);
        }
        return quotation;
    }

    @Override
    public List<Quotation> readAllActiveQuotations() {
        return quotationRepository.findByStatus(1);
    }

    @Override
    public List<Quotation> readAllRemovedQuotations() {
        return quotationRepository.findByStatus(0);
    }

    @Override
    public void updateQuotation(Quotation quotation) {
        if (quotationRepository.findById(quotation.getId()) != null) {
            quotationRepository.save(quotation);
        } else {
            throw new ResourceNotFoundException(clase);
        }
    }

    @Override
    public void deleteQuotation(int id) {
        quotationRepository.deleteById(id);
    }

    @Override
    public void softDeleteQuotation(int id) {
        Quotation quotation = quotationRepository.findById(id);
        if (quotation != null) {
            quotation.setStatus(0);
            for (QuotationDetail quotationDetail : quotation.getQuotationDetails()) {
                quotationDetail.setStatus(0);
            }
            quotationRepository.save(quotation);
        } else {
            throw new ResourceNotFoundException(clase);
        }
    }

    @Override
    public List<PurchaseHeader> makePurchaseFromOrder(int idOrder) {
        List<QuotationDetail> quotationDetails = new ArrayList<>();
        Order order = orderRepository.findById(idOrder);
        List<PurchaseHeader> purchaseHeaders = new ArrayList<>();
        if (order != null) {
            if (order.getQuotations().size() >= 2) {
                //logica para cuando existen más de 2 cotizaciones...
                for (int i = 0; i < order.getQuotations().size() - 1; i++) {
                    Quotation quotation = order.getQuotations().get(i);
                    Quotation quotationNext = order.getQuotations().get(i + 1);
                    QuotationDetail currentDetail = null;
                    for (int j = 0; j < quotation.getQuotationDetails().size(); j++) {
                        QuotationDetail quotationDetail = quotation.getQuotationDetails().get(j);
                        QuotationDetail quotationDetailNext = quotationNext.getQuotationDetails().get(j);
                        if (quotationDetail == null) {
                            currentDetail = quotationDetailNext;
                        } else if (quotationDetailNext == null) {
                            currentDetail = quotationDetail;
                        } else if (quotationDetail.getTotalPrice() <= quotationDetailNext.getTotalPrice()) { //se extraen los menores precios
                            if (quotationDetail.getTotalPrice() > 0) {
                                currentDetail = quotationDetail;
                            } else {
                                currentDetail = quotationDetailNext;
                            }
                        } else {
                            if (quotationDetailNext.getTotalPrice() > 0) {
                                currentDetail = quotationDetailNext;
                            } else {
                                currentDetail = quotationDetail;
                            }
                        }
                        quotationDetails.add(currentDetail);
                    }
                }
                //tenemos en este punto la lista de las mejores cotizaciones por producto, tenemos que armar la(s) venta(s) y su detalle
                List<PurchaseDetail> purchaseDetails = new ArrayList<>();
                Collections.sort(quotationDetails, QuotationDetail.QuotationDetailOrderByIdQuotation); //Se ordenan los detalles de Quotation mediante el id de Quotation
                int idQuotation = 0;
                int cont = 1;
                PurchaseHeader purchaseHeader = new PurchaseHeader();
                for (int i = 0; i <= quotationDetails.size() - 1; i++) {
                    QuotationDetail quotationDetail = quotationDetails.get(i);
                    int idNextQuotation;
                    try {
                        idNextQuotation = quotationDetails.get(i + 1).getQuotation().getId();
                    } catch (Exception e) {
                        idNextQuotation = 0;
                    }
                    idQuotation = quotationDetail.getQuotation().getId(); //mantenemos el id de la ultima Quotation
                    PurchaseDetail purchaseDetail = makePurchaseDetail(quotationDetail);
                    purchaseDetails.add(purchaseDetail);
                    if (idQuotation != idNextQuotation || cont == quotationDetails.size()) { //hacemos un cambio si el Id de la quotation es diferente
                        purchaseHeader.setPurchaseDetails(purchaseDetails);
                        purchaseHeader.setStatus(1);
                        purchaseHeader.setFolio("");
                        purchaseHeader.setSeries("C");
                        purchaseHeader.setProvider(quotationDetail.getQuotation().getProvider());
                        purchaseDetails = new ArrayList<>(); //se limpia para la siguiente iteraccion
                        purchaseHeaders.add(purchaseHeader);
                        purchaseHeader = new PurchaseHeader(); //limpiamos para la siguiente venta
                        continue; //saltamos a crear una nueva venta
                    }
                    cont++;
                }
            } else {
                //logica para cuando es al menos 1 cotización
                if (order.getQuotations().size() == 1) {
                    PurchaseHeader purchaseHeader = new PurchaseHeader();
                    purchaseHeader.setStatus(1);
                    purchaseHeader.setFolio("");
                    purchaseHeader.setSeries("C");
                    purchaseHeader.setProvider(order.getQuotations().get(0).getProvider());
                    List<PurchaseDetail> purchaseDetails = new ArrayList<>();
                    for (QuotationDetail quotationDetail : order.getQuotations().get(0).getQuotationDetails()) {
                        PurchaseDetail purchaseDetail = makePurchaseDetail(quotationDetail);
                        purchaseDetails.add(purchaseDetail); //se agrega a la lista
                    }
                    purchaseHeader.setPurchaseDetails(purchaseDetails); //se agrega la lista a su cabecera
                    purchaseHeaders.add(purchaseHeader); //se agrega a la variable de retorno
                } else {
                    throw new NoContentException(PurchaseHeader.class.getSimpleName(), "purchaseDetails[]");
                }
            }
        } else {
            throw new ResourceNotFoundException(Order.class.getSimpleName());
        }
        return purchaseHeaders;
    }

    public static PurchaseDetail makePurchaseDetail(QuotationDetail quotationDetail) {
        PurchaseDetail purchaseDetail = new PurchaseDetail();
        purchaseDetail.setStatus(1);
        purchaseDetail.setQuantity(quotationDetail.getQuantity());
        purchaseDetail.setCurrentAmount(quotationDetail.getQuantity());
        purchaseDetail.setUnitPrice(quotationDetail.getUnitPrice());
        purchaseDetail.setItem(quotationDetail.getItem());
        return purchaseDetail;
    }
}
