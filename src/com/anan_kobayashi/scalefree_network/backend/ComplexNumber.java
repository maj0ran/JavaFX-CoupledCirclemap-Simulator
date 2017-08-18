package com.anan_kobayashi.scalefree_network.backend;

/*
 *
 * Created by marian on 5/7/2017.
 *
 */
public class ComplexNumber {

    public double real;
    public double imaginary;

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public void add(ComplexNumber z) {
        this.real += z.real;
        this.imaginary += z.imaginary;
    }

    public void sub(ComplexNumber z) {
        this.real -= z.real;
        this.imaginary -= z.imaginary;
    }

    public void mul(ComplexNumber z) {
        this.real = this.real * z.real - this.imaginary * z.imaginary;
        this.imaginary = this.real * z.imaginary + z.real * this.imaginary;
    }
}
