package tda.darkarmy.onlinemarketplace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tda.darkarmy.onlinemarketplace.dto.ProductDto;
import tda.darkarmy.onlinemarketplace.service.ProductService;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        return status(200).body(productService.getAll());
    }

    @GetMapping("/my-products")
    public ResponseEntity<?> findMyProducts(){
        return status(200).body(productService.findMyProducts());
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long ProductId){
        return status(200).body(productService.findById(ProductId));
    }

    @PostMapping()
    public ResponseEntity<?> addProduct( @ModelAttribute ProductDto productDto){
        return status(201).body(productService.addProduct(productDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long ProductId, @RequestBody ProductDto productDto){
        return status(200).body(productService.updateProduct(ProductId, productDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long ProductId){
        return status(200).body(productService.deleteProduct(ProductId));
    }
}
