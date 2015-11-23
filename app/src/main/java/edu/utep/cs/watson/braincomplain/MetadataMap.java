package edu.utep.cs.watson.braincomplain;


public class MetadataMap
{
    String abstractStr;
    String originalfile;
    String author;
    String title;
    String corpusName;
    String description;
    String deepqaid;
    String fileName;
    String DOCNO;

    public MetadataMap(String abstractStr, String originalfile, String author, String title, String corpusName, String description, String deepqaid, String fileName, String DOCNO) {
        this.abstractStr = abstractStr;
        this.originalfile = originalfile;
        this.author = author;
        this.title = title;
        this.corpusName = corpusName;
        this.description = description;
        this.deepqaid = deepqaid;
        this.fileName = fileName;
        this.DOCNO = DOCNO;
    }

    public MetadataMap() {
    }

    public String getAbstractStr() {
        return abstractStr;
    }

    public void setAbstractStr(String abstractStr) {
        this.abstractStr = abstractStr;
    }

    public String getOriginalfile() {
        return originalfile;
    }

    public void setOriginalfile(String originalfile) {
        this.originalfile = originalfile;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCorpusName() {
        return corpusName;
    }

    public void setCorpusName(String corpusName) {
        this.corpusName = corpusName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeepqaid() {
        return deepqaid;
    }

    public void setDeepqaid(String deepqaid) {
        this.deepqaid = deepqaid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDOCNO() {
        return DOCNO;
    }

    public void setDOCNO(String DOCNO) {
        this.DOCNO = DOCNO;
    }
}

