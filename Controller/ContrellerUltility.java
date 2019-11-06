package Controller;

import Model.Management;

import java.util.ArrayList;

/*
Sap xep
Tim kiem
Cap nhat lai danh sach
 */
public class ContrellerUltility {
    public ArrayList<Management> updateBRM(ArrayList<Management> list, Management brm ){
        boolean isUpdate = false;


        for(int i = 0; i < list.size(); i++){
            Management b = list.get(i);
            if(b.getBook().getBookID() == brm.getBook().getBookID() &&
                b.getReader().getReaderID() == brm.getReader().getReaderID())
            {
                list.set(i,brm);
                isUpdate = true;
                break;
            }
        }

        if(!isUpdate)
            list.add(brm);
        return list;
    }
}
