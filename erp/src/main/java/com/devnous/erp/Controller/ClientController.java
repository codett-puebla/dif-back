package com.devnous.erp.Controller;

import com.devnous.erp.Constants.Endpoints;
import com.devnous.erp.Entity.Client;
import com.devnous.erp.Service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoints.CLIENT)
@Api("CLIENT MODULE")
public class ClientController {

    @Autowired
    @Qualifier("clientService")
    ClientService clientService;

    @ApiOperation("Obtener todos los clientes activos")
    @GetMapping("/active/company={idCompany}")
    public ResponseEntity<List<Client>> getAllActiveClients(@PathVariable("idCompany") int idCompany) {
        List<Client> clients = clientService.readAllActiveClient(idCompany);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @ApiOperation("Obtener todos los clientes removidos")
    @GetMapping("/removed/company={idCompany}")
    public ResponseEntity<List<Client>> getAllRemovedClients(@PathVariable("idCompany") int idCompany) {
        List<Client> clients = clientService.readAllRemovedClient(idCompany);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @ApiOperation("Obtiene un cliente por Id")
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable("id") int id) {
        Client client = clientService.readClient(id);
        return new ResponseEntity<Client>(client, HttpStatus.OK);
    }

    @ApiOperation("Insertar un cliente")
    @PostMapping("/new")
    public ResponseEntity<String> insertClient(@RequestBody Client client) {
        clientService.createClient(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation("Elimina un cliente(cambio de status)")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSoftClient(@PathVariable("id") int id) {
        clientService.softDeleteClient(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @ApiOperation("Actualiza un cliente")
    @PutMapping(path = "", consumes = "application/json")
    public ResponseEntity<String> updateClient(@RequestBody Client client) {
        clientService.updateClient(client);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @ApiOperation("Elimina un cliente de la BD")
    @DeleteMapping(path = "/hard={id}")
    public ResponseEntity<String> deleteClient(@PathVariable("id") int id) {
        clientService.deleteClient(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
