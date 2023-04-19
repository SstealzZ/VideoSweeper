package fr.sstealzz.data;

public class Data {
    public enum DataType {
        NameFile, SizeFile, CompressedSizeFile, CompressionRatio, isCompressed
    }

    private String nameFile;
    private long sizeFile;
    private long compressedSizeFile;
    private double compressionRatio;
    private boolean isCompressed;

    public Data(String nameFile, long sizeFile, long compressedSizeFile, double compressionRatio, boolean isCompressed) {
        this.nameFile = nameFile;
        this.sizeFile = sizeFile;
        this.compressedSizeFile = compressedSizeFile;
        this.compressionRatio = compressionRatio;
        this.isCompressed = isCompressed;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public long getSizeFile() {
        return sizeFile;
    }

    public void setSizeFile(long sizeFile) {
        this.sizeFile = sizeFile;
    }

    public long getCompressedSizeFile() {
        return compressedSizeFile;
    }

    public void setCompressedSizeFile(long compressedSizeFile) {
        this.compressedSizeFile = compressedSizeFile;
    }

    public double getCompressionRatio() {
        return compressionRatio;
    }
    
    public void setCompressionRatio(double compressionRatio) {
        this.compressionRatio = compressionRatio;
    }

    public boolean isCompressed() {
        return isCompressed;
    }

    public void setCompressed(boolean isCompressed) {
        this.isCompressed = isCompressed;
    }
}
