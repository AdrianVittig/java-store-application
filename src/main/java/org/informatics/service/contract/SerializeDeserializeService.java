package org.informatics.service.contract;

import org.informatics.entity.Receipt;

import java.io.IOException;

public interface SerializeDeserializeService {
    //TODO SERIALIZING SERVICE
    void serializeReceipt(String fileName, Receipt receipt) throws IOException;

    Receipt deserializeReceipt(String fileName) throws IOException, ClassNotFoundException;
}
