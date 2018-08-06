package com.ltime.buspad.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.ltime.buspad.R;

import java.io.File;

public class PdfActivity extends Activity implements OnPageChangeListener {

    PDFView pdfView;
    TextView text;
    String pdfName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        pdfView = findViewById(R.id.pdfView);
        text=findViewById(R.id.text);
        pdfName = Environment.getExternalStorageDirectory() +
                "/temp";
        display(pdfName, false);

    }


    private void display(String assetFileName, boolean jumpToFirstPage) {
        if (jumpToFirstPage)
            setTitle(pdfName = assetFileName);
        File file = new File(assetFileName, "pdf.pdf");
        pdfView.fromFile(file)
                //                .pages(0, 0, 0, 0, 0, 0) // 默认全部显示，pages属性可以过滤性显示
                .defaultPage(1)//默认展示第一页
                .onPageChange(this)//监听页面切换
                .load();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        text.setText(page + "/" + pageCount);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
