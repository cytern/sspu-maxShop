package com.funong.funong.plugin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * @Author: cytern
 * @Date: 2020/5/26 15:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetExcelUrl {
    private String baseUrl;
    private String linuxBaseIp="http://62.234.29.109:2020/";
    private String windowsBaseIp="http://localhost:2020/";
    private String downloadUrl = "everyone/downLoad/";
    private String linuxStorage="/export/data/excel/";
    private String windowsStorage="F:\\excel\\";
    private String export;
    private String storage;
    private String downloadStorage;
    private String httpUrl;
    private String ip = "";
    public String getBaseUrl(){
        this.baseUrl = System.getProperty("os.name");

        if (this.baseUrl.contains("Windows")){
            this.export = this.windowsBaseIp + this.downloadUrl;
            this.storage = this.windowsStorage;
            this.downloadStorage = this.windowsStorage + "download\\";
            this.httpUrl = this.windowsBaseIp;
            this.ip = "http://localhost:8080";
            File file1 = new File(this.storage);
            if (!file1.exists()){
                file1.mkdir();
            }
            File file2 = new File(this.downloadStorage);
            if (!file2.exists()){
                file2.mkdir();
            }
        }else {
            this.export = this.linuxBaseIp + this.downloadUrl;
            this.storage = this.linuxStorage;
            this.downloadStorage = this.linuxStorage + "download/";
            this.ip = "http://62.234.29.109:8080";
            this.httpUrl = this.linuxBaseIp;
            File file1 = new File(this.storage);
            if (!file1.exists()){
                file1.mkdir();
            }
            File file2 = new File(this.downloadStorage);
            if (!file2.exists()){
                file2.mkdir();
            }
        }

        return null;

    }
}
