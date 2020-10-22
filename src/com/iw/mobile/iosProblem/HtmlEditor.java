/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.mobile.iosProblem;

import com.codename1.components.WebBrowser;
import com.codename1.javascript.JSObject;
import com.codename1.javascript.JavascriptContext;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import java.io.IOException;
import java.io.InputStream;


/**
 *
 * @author helio
 */
public class HtmlEditor extends WebBrowser {
    
    private JavascriptContext jsCtx;
    final private String initContent;
    static private String htmlSeed;
    
    public HtmlEditor(String initContent) {
        super();
        this.initContent = initContent;
        if (htmlSeed == null) {
            htmlSeed = readFile("/MySummerNoteSeed.html");
        }
        else {
            setPage(htmlSeed, null);
            repaint();
        }
    }
    
    private String readFile(String fileName) {
        
        String resp = null;
        InputStream in =
            Display.getInstance().getResourceAsStream(Form.class, fileName);
        
        if (in != null){
            try {
                resp = com.codename1.io.Util.readToString(in);
                in.close();
            } catch (IOException ex) {
                System.out.println(ex);
                resp = "Read Error";
            }
        }
        
        return resp;
    }    
            
    public JavascriptContext getJavascriptContext() {
        return jsCtx;
    }

    @Override
    public void onLoad(String url) {
        BrowserComponent c = (BrowserComponent)this.getInternal();
        c.setPinchToZoomEnabled(true);
        jsCtx = new JavascriptContext(c);
        setIwText(initContent);
    }
    
    public void setIwText(String s) {
        if (s == null || s.trim().equals("")) {
            //s = "<p>Escreva abaixo o texto livre:</p>";
            s = "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>";
        }  
        if (jsCtx != null) {
            JSObject jsObj= (JSObject) jsCtx.get("$('#summernote')");
            jsObj.call("code" , new Object[]{s});
        }
    }
    
    public String getIwText() {
        if (jsCtx != null) {
            return (String) jsCtx.get("$('#summernote').code()");
        }
        return "Error : Jascript context is null";
    }
    
}
