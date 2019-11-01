package com.devnous.erp.ServiceImpl;

import com.devnous.erp.Service.HelperFolioService;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service("helperFolioService")
public class HelperServiceImpl implements HelperFolioService {

    @Override
    public String createNewFolio(String lastFolio) {
        String newFolio = ""; //format folio is 0001-02-19 -> numberFolio-month-year
        String numberFolio = "0001";
        String month = "";
        String year = "";
        String lastMonth = "";
        String lastYear = "";
        if (lastFolio.length() == 10) { //check if the length is equals to the format
            String[] array = lastFolio.split("-");
            lastMonth = array[1];
            lastYear = array[2];
        } else {
            lastFolio = "0000-00-00";
        }
        Calendar calendar = Calendar.getInstance();

        month = String.format("%02d",Integer.valueOf(String.valueOf(calendar.get(Calendar.MONTH) + 1)));
        year = String.valueOf(calendar.get(Calendar.YEAR)).substring(2,4);


        boolean flag = false;
        try {
            flag = Integer.valueOf(month) == Integer.valueOf(lastMonth) && Integer.valueOf(year) == Integer.valueOf(lastYear);
        } catch (Exception e) {
            //nothing
            System.out.println(e);
        }

        //If month or the year is different we need to increment the last folio, otherwise we init the folio to 0001
        if (flag) {
            String[] array = lastFolio.split("-");
            numberFolio = array[0];
            numberFolio = String.valueOf(Integer.valueOf(numberFolio)+1);
            numberFolio = String.format("%04d", Integer.valueOf(numberFolio));
        }

        return numberFolio + "-" + month + "-" + year;
    }


}
