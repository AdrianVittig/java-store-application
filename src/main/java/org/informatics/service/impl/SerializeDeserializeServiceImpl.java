package org.informatics.service.impl;

import org.informatics.entity.Receipt;
import org.informatics.service.contract.SerializeDeserializeService;

import java.io.*;

public class SerializeDeserializeServiceImpl implements SerializeDeserializeService {
    @Override
    public void serializeReceipt(String fileName, Receipt receipt) throws IOException {
        try(FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                objectOutputStream.writeObject(receipt);
        }
    }

    @Override
    public Receipt deserializeReceipt(String fileName) throws IOException, ClassNotFoundException {
        Receipt receipt = null;
        try(FileInputStream fileInputStream = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            receipt = (Receipt) objectInputStream.readObject();
        }
        System.out.println("DESERIALIZED");
        if(receipt != null) {
            System.out.println(receipt);
        }
        return receipt;
    }
}
