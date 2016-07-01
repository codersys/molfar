package net.molfar.features;

import java.awt.image.BufferedImage;

public interface IExtractor {
    
    void extract(BufferedImage image);
    
    byte[] getVector();
    
    byte[] getShortVector();

    void adjustment( BufferedImage[] sample );
    
    void adjustment( byte[] mask );
    
    int getMaxLength();

    int getCurrLength();
    
    String getName();
}
