package com.tao.insurance.controller;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.tao.insurance.common.ApiResponse;
import com.tao.insurance.engine.InsuranceCalculationEngine;
import com.tao.insurance.entity.BaoDanResult;
import com.tao.insurance.entity.PdfRequest;
import com.tao.insurance.entity.PolicyQueryRequest;

import com.tao.insurance.service.BaoDanResultService;
import com.tao.insurance.service.MakeTextService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 理赔标准查询接口
 */
@CrossOrigin
@RestController
@RequestMapping("/api/insurance")
@RequiredArgsConstructor
public class InsuranceController {

@Autowired
private MakeTextService makeTextService;
@Autowired
private BaoDanResultService baoDanResultService;
    /**
     * POST /api/insurance/standard
     * 入参：InsuranceInput（JSON）
     */
    @PostMapping("/describe")
    public ApiResponse<Object> describePolicy(@RequestBody PolicyQueryRequest req) {
        return makeTextService.makeText(req);

    }

    @PostMapping("/generatePdf")
    public ApiResponse<Object> generatePdf(@RequestBody PdfRequest para) {
        String timeStp= String.valueOf(System.currentTimeMillis());
        String pdfPath = "/home/cloudtao/Projects/doc/BaoDanPDF/" + timeStp + ".pdf";
        BaoDanResult bdr=new BaoDanResult();
        String[] a = para.getTimeRange().substring(1, para.getTimeRange().length()-1).split(",");
        DateTimeFormatter f1 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss"), f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String begin = LocalDateTime.parse(a[0], f1).format(f2), end = LocalDateTime.parse(a[1], f1).format(f2);

        bdr.setInsuranceType(para.getInsuranceType());
        bdr.setBeginTime(begin);
        bdr.setEndTime(end);
        bdr.setPdfUrl("/doc_dm/BaoDanPDF/"+timeStp+".pdf");
        String now = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        bdr.setCreateTime(now);
        bdr.setInsuranceName(para.getInsuranceName());
        return generatePdf(para.getDescription(), pdfPath,bdr);
    }



    public ApiResponse<Object> generatePdf(String text, String outputPath, BaoDanResult bdr) {
        try (Document document = new Document()) {

            PdfWriter.getInstance(document, Files.newOutputStream(Paths.get(outputPath)));
            document.open();

            // 原本的中文字体（保持不变）
            BaseFont bf = BaseFont.createFont(
                    "STSong-Light",
                    "UniGB-UCS2-H",
                    BaseFont.NOT_EMBEDDED
            );
            Font font = new Font(bf, 12, Font.NORMAL);

            float maxWidth = document.getPageSize().getWidth()
                    - document.leftMargin()
                    - document.rightMargin();

            // 自动分行
            List<String> lines = wrapText(bf, text, 12, maxWidth);

            for (String line : lines) {
                Paragraph p = new Paragraph(line, font);
                p.setLeading(18f);
                document.add(p);
            }

            baoDanResultService.save(bdr);
            return ApiResponse.success(bdr.getPdfUrl());

        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.fail("生成失败！");
        }
    }
    public  List<String> wrapText(BaseFont bf, String text, float fontSize, float maxWidth) {
        List<String> lines = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            sb.append(c);

            float width = bf.getWidthPoint(sb.toString(), fontSize);

            // 超出宽度 → 换行
            if (width > maxWidth) {
                sb.deleteCharAt(sb.length() - 1); // 去掉当前字符
                lines.add(sb.toString());

                sb = new StringBuilder();
                sb.append(c); // 下行从当前字符继续
            }
        }

        if (sb.length() > 0) {
            lines.add(sb.toString());
        }

        return lines;
    }

}
