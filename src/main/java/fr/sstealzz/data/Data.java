package fr.sstealzz.data;

public class Data {
    public enum Types {
        NameFile, SizeFile, CompressedSizeFile, CompressionRatio, isCompressed
    }

    private String nameFile;
    private long sizeFile;
    private long compressedSizeFile;

    public Data(String nameFile, long sizeFile, long compressedSizeFile) {
        this.nameFile = nameFile;
        this.sizeFile = sizeFile;
        this.compressedSizeFile = compressedSizeFile;
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
        return (double) compressedSizeFile / sizeFile;
    }

    public boolean isCompressed() {
        return compressedSizeFile < sizeFile;
    }

}
