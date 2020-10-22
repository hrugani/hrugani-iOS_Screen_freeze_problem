package com.iw.mobile.iosProblem;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;


/**
 *
 * @author helio
 */
public class Form2 extends Form {
    
    static public final int USER_ACTION_CONFIRM = 1;
    static public final int USER_ACTION_CANCEL = 0;
    private int userAction = USER_ACTION_CANCEL;
    
    private HtmlEditor editor;
    private Button btnConfirm;
    private Button btnCancel;
    
    private String htmlPieceInit;
    private String htmlPieceEdited;
    
    private String varName;

    public Form2 () {
        
        super();
        setTitle("Html Editor");
        this.varName = "TESTE";
        this.htmlPieceInit =  "<p>This is a test...</p>";
        this.htmlPieceEdited = "<p>This is a test...</p>";
        this.setScrollable(false);
        preInit();
        init();
        postInit();    
    }
    
    public void setMyBackCmd(Command c) {
        getToolbar().addMaterialCommandToLeftBar(null, FontImage.MATERIAL_ARROW_BACK, c); 
        setBackCommand(c);
    }
    
    
    public String getHtmlPieceEdited() {
        return this.htmlPieceEdited;
    }

    public int getUserAction() {
        return this.userAction;
    }
    
    private void preInit() {
        editor = createEditor();
        btnConfirm = createBtnConfirm();
        btnCancel = createBtnCancel();
    }

    private void init() {
       Container pnlButtons = new Container(new GridLayout(1,2));
       pnlButtons.addComponent(btnConfirm);
       pnlButtons.addComponent(btnCancel);
       
       Container pnlCenter = new Container(new BorderLayout());
       pnlCenter.setScrollableY(false);
       pnlCenter.addComponent(BorderLayout.CENTER, editor);
       
       getContentPane().setLayout(new BorderLayout());
       getContentPane().setScrollableY(false);
       getContentPane().addComponent(BorderLayout.CENTER, pnlCenter);
       getContentPane().addComponent(BorderLayout.SOUTH, pnlButtons);
    }

    private void postInit() {
        editor.requestFocus();
    }

    private HtmlEditor createEditor() {
        HtmlEditor e = new HtmlEditor(this.htmlPieceInit);
        return e;
    }

    private Button createBtnConfirm() {
        Button btn = new Button("Confirm");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                userAction = USER_ACTION_CONFIRM;
                htmlPieceEdited = editor.getIwText();
                
                if (htmlPieceEdited.indexOf("\\") != -1) {
                    String msgErr = "Barra invertida [\\]\nnão permitido.";                  
                    Dialog.show("Erro", msgErr, "OK", null);
                    return;
                }
                
                getBackCommand().actionPerformed(null);
                
            }
        });
        return btn;
    }

    private Button createBtnCancel() {
        Button btn = new Button("Cancel");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                userAction = USER_ACTION_CANCEL;
                getBackCommand().actionPerformed(null);
            }
        });
        return btn;
    }
    
        
    
    
}
