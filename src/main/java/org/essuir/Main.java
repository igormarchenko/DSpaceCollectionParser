package org.essuir;

import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) throws IOException {
        String command = Config.getDspacePath() + " -d -t AIP -e " + Config.getSubmitterEmail() + " -i 123456789/%s /home/admin-sumdu/dspace-temp/ITEM-%s.zip";

        DspaceSpider dspaceSpider = new DspaceSpider(Config.getCollectionId());
        dspaceSpider.parse();

        PrintWriter writer = new PrintWriter("result.txt");
        dspaceSpider.getUrls().forEach(item -> writer.println(String.format(command, item, item)));
        writer.flush();
        writer.close();
    }
}
