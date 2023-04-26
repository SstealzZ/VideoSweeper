package fr.sstealzz.data;

public class Data {
    public enum Types {
        NameFile, SizeFile, CompressedSizeFile, CompressionRatio, isCompressed, SizeFileInMo, SizeFileInGo, pathFile
    }

    private String nameFile;
    private long sizeFile;
    private long compressedSizeFile;
    private String pathFile;

    public Data(String nameFile, String pathFile, long sizeFile, long compressedSizeFile) {
        this.nameFile = nameFile;
        this.pathFile = pathFile;
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
    
    public long getSizeFileInMo() {
        if (sizeFile < 1000000) {
            return sizeFile;
        } else {
            return sizeFile / 1000000;
        }
    }

    public long getSizeFileInGo() {
        if (sizeFile < 1000000000) {
            return getSizeFileInMo();
        } else {
            return sizeFile / 1000000000;
        }
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

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

}
