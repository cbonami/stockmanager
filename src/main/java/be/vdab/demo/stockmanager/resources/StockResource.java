package be.vdab.demo.stockmanager.resources;

import be.vdab.demo.stockmanager.exceptions.StockNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import be.vdab.demo.stockmanager.model.Stock;
import be.vdab.demo.stockmanager.services.StockService;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockResource {

    @Autowired
    private StockService stockService;

    @GetMapping("/v1.0")
    public List<Stock> getStocks() {
        return stockService.getStocks();
    }

    @GetMapping("/v1.0/{sku}")
    public Stock getStock(@PathVariable("sku") String sku) throws StockNotFoundException {
        return stockService.getStock(sku);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleStockNotFound(StockNotFoundException snfe) {
    }
}
