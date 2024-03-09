package com.project.SWP391.controllers;

import com.project.SWP391.responses.dto.OrderInfoDTO;
import com.project.SWP391.services.OrderService;
import com.project.SWP391.services.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.project.SWP391.contraints.Contraints.*;


@Controller
@CrossOrigin
@RequestMapping("/api/v1/base")
public class PaypalController {

    @Autowired
    private PaypalService service;

    @Autowired
    private OrderService orderService;

   @RequestMapping(value = "/checkout/{id}", method = RequestMethod.POST)
   public ResponseEntity<String> payment(@PathVariable(name="id") Long id) {
            return ResponseEntity.ok(orderService.payAnOrder(id));
  }

    @RequestMapping(value = CANCEL_URL, method = RequestMethod.POST)
    public String cancelPay() {
        return "cancel";
    }

//    @RequestMapping(value = SUCCESS_URL, method = RequestMethod.POST)
//    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
//      return  orderService.confirmSuccessPaypal(paymentId,payerId);
//    }
@GetMapping("/confirm")
public ResponseEntity<OrderInfoDTO> successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {

return ResponseEntity.ok(orderService.confirmSuccessPaypal(paymentId, payerId));
}


}
