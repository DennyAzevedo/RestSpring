package br.dev.md.controllers;

import br.dev.md.exception.UnsupportedMathOperationException;
import org.jetbrains.annotations.Contract;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class MathController {
    // http://localhost:8080/math/sum/3/5
    @RequestMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(
        @PathVariable("numberOne")
        String numberOne,
        @PathVariable("numberTwo")
        String numberTwo
    ) throws Exception {
        if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        return convertToDouble(numberOne) + convertToDouble(numberTwo);
    }

    // http://localhost:8080/math/subtract/3/5
    @RequestMapping("/subtract/{numberOne}/{numberTwo}")
    public Double subtract(
        @PathVariable("numberOne")
        String numberOne,
        @PathVariable("numberTwo")
        String numberTwo
    ) throws Exception {
        if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        return convertToDouble(numberOne) - convertToDouble(numberTwo);
    }

    // http://localhost:8080/math/multiply/3/5
    @RequestMapping("/multiply/{numberOne}/{numberTwo}")
    public Double multiply(
        @PathVariable("numberOne")
        String numberOne,
        @PathVariable("numberTwo")
        String numberTwo
    ) throws Exception {
        if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        return convertToDouble(numberOne) * convertToDouble(numberTwo);
    }

    // http://localhost:8080/math/divide/3/5
    @RequestMapping("/divide/{numberOne}/{numberTwo}")
    public Double divide(
            @PathVariable("numberOne")
            String numberOne,
            @PathVariable("numberTwo")
            String numberTwo
    ) throws Exception {
        if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        if (numberTwo.equals("0")) {
            throw new UnsupportedMathOperationException("Division by zero is not allowed");
        }
        return convertToDouble(numberOne) / convertToDouble(numberTwo);
    }
    
    // http://localhost:8080/math/average/3/5
    @RequestMapping("/average/{numberOne}/{numberTwo}")
    public Double average(
        @PathVariable("numberOne")
        String numberOne,
        @PathVariable("numberTwo")
        String numberTwo
    ) throws Exception {
        if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        return (convertToDouble(numberOne) + convertToDouble(numberTwo)) / 2;
    }
    
    // http://localhost:8080/math/squareRoot/9
    @RequestMapping("/squareroot/{numberOne}")
    public Double squareRoot(
        @PathVariable("numberOne")
        String numberOne
    ) throws Exception {
        if(!isNumeric(numberOne)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        double number = convertToDouble(numberOne);
        if (number < 0) {
            throw new UnsupportedMathOperationException("Negative numbers cannot have a square root");
        }
        return Math.sqrt(number);
    }
    
    @Contract("null -> false")
    private boolean isNumeric(String strNumber) {
        if (strNumber == null || strNumber.isEmpty()) {
            return false;
        }
        String number = strNumber.replaceAll(",", ".");
        
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
    
    private Double convertToDouble(String strNumber) throws IllegalAccessException {
        if (strNumber == null || strNumber.isEmpty()) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        String number = strNumber.replaceAll(",", ".");
        
        return Double.parseDouble(number);
    }
}
