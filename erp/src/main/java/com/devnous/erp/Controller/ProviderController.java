package com.devnous.erp.Controller;

import com.devnous.erp.Constants.Endpoints;
import com.devnous.erp.Entity.Provider;
import com.devnous.erp.Service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoints.PROVIDER)
public class ProviderController {
    @Autowired
    @Qualifier("providerService")
    ProviderService providerService;

    @GetMapping("/active")
    public ResponseEntity<List<Provider>> getAllActiveProviders() {
        List<Provider> providers = providerService.readAllActiveProvider();
        return new ResponseEntity<>(providers, HttpStatus.OK);
    }

    @GetMapping("/removed")
    public ResponseEntity<List<Provider>> getAllRemovedProviders() {
        List<Provider> providers = providerService.readAllRemovedProvider();
        return new ResponseEntity<>(providers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Provider> getProvider(@PathVariable("id") int id) {
        Provider provider = providerService.readProvider(id);
        return new ResponseEntity<Provider>(provider, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<String> insertProvider(@RequestBody Provider provider) {
        providerService.createProvider(provider);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSoftProvider(@PathVariable("id") int id) {
        providerService.softDeleteProvider(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping(path = "", consumes = "application/json")
    public ResponseEntity<String> updateProvider(@RequestBody Provider provider) {
        providerService.updateProvider(provider);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/hard={id}")
    public ResponseEntity<String> deleteProvider(@PathVariable("id") int id) {
        providerService.deleteProvider(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
