package com.secquan.ui;


import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTextField;
/**
 * 自定义文本框输入类 为了实现文件夹拖动
 * <p><b>Title: </b>MyTextField.java
 * <p><b>Description: </b>TODO
 * @author wyyw
 * @version V1.0
 * <p>
 * Jun 29, 2016 wyyw 创建类  <br>
 *
 */
public class MyTextField  extends JTextField implements DropTargetListener {
	public MyTextField(){
        new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, this);
    }
    public void dragEnter(DropTargetDragEvent dtde) {
    }

    public void dragOver(DropTargetDragEvent dtde) {
    }

    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    public void dragExit(DropTargetEvent dte) {
    }

    public void drop(DropTargetDropEvent dtde) {
        try {
            Transferable tr = dtde.getTransferable();

            if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
               
                List list = (List) (dtde.getTransferable()
                        .getTransferData(DataFlavor.javaFileListFlavor));
                Iterator iterator = list.iterator();
                while (iterator.hasNext()) {
                    File f = (File) iterator.next();
                    if(f.isFile()){
                    	this.setText(f.getAbsolutePath());
                    	ForceShellPanel.callBack();
                    }else{
                    	this.setText("请选择文件 ");
                    
                    }
                }
                dtde.dropComplete(true);
                this.updateUI();
            }else {
                dtde.rejectDrop();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (UnsupportedFlavorException ufe) {
            ufe.printStackTrace();
        }
    } 
}
