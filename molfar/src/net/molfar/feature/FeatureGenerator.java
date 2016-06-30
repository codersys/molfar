package net.molfar.feature;

import java.util.*;
import java.awt.image.BufferedImage;

public class FeatureGenerator {
    
    private List<IExtractor> extractorList = new ArrayList<IExtractor>();
    private int features_num = 0;
    private int features_num_actual = 0;
    
    /*
     * Перед добавлением, екстракторы могут быть сконфигурированы, настроенны
     */
    public void addExtractor(IExtractor extractor){
        extractorList.add( extractor );
        features_num += extractor.getCurrLength();
    }
    
    /*
     * Удаление екстрактора по его имени
     */
    public void delExtractor(String name){
        for(IExtractor e : extractorList){
            if(e.getName().equals(name)) {
                features_num -= e.getMaxLength();
                features_num_actual -= e.getCurrLength();
                extractorList.remove(e);
                break;
            }
        }
    }
    
    /*
     * Возвращает екстрактор по номеру порядка его добавления
     */
    public IExtractor getExtractor(int id){
        return extractorList.get( id );
    }
    
    /*
     * 
     */
    public byte[] getVector(BufferedImage image){
        byte[] res = new byte[features_num];
        int count = 0;
        for(IExtractor e : extractorList){
            e.extract(image);
            byte[] vector = e.getVector();
            System.arraycopy( vector, 0, res, count, e.getMaxLength() );
            count += e.getMaxLength();
        } 
        return res;
    }
    
    /*
     * 
     */
    public byte[] getShortVector(BufferedImage image){
        byte[] res = new byte[getCurrLength()];
        int count = 0;
        for(IExtractor e : extractorList){
            e.extract(image);
            byte[] vector = e.getShortVector();
            System.arraycopy( vector, 0, res, count, e.getCurrLength() );
            count += e.getCurrLength();
        }
        return res;
    }
    
    /*
     * Задает маску для полного вектора признаков, режется на кусочки 
     * что бы применить к каждому конкретному екстрактору
     */
    public void adjustment( byte[] mask ){
        int count = 0;
        for(IExtractor e : extractorList){
            byte[] submask = new byte[e.getMaxLength()];
            System.arraycopy( mask, 0, submask, count, e.getMaxLength() );
            count += e.getMaxLength();
        }
    }
    
    /*
     * Возвращает максимальную длинну дескриптора
     */
    int getMaxLength(){
        return features_num;
    }
    
    /*
     * Возвращает текущую длинну дескриптора
     */
    int getCurrLength(){
        features_num_actual = 0;
        for(IExtractor e : extractorList) features_num_actual += e.getCurrLength();
        return features_num_actual;
    }
}
