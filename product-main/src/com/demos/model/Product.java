package com.demos.model;
import java.util.*;

public class Product
{
    private String mBarcode;
    private String mName;
    private String mDescription;
    private String mColor;
    
    public String GetBarcode()
    {
        return mBarcode;
    }
    public String GetName()
    {
        return mName;
    }
    public String GetDescription()
    {
        return mDescription;
    }
    public String GetColor()
    {
        return mColor;
    }
    public Product(String bc, String name, String desc, String col)
    {
        mBarcode = bc;
        mName = name;
        mDescription = desc;
        mColor = col;
    }
    public String toHtml()
    {
        return "Barcode: "+mBarcode+"<br>Name: "+mName+"<br>Description: "+mDescription+"<br>Color: "+mColor;
    }

}

