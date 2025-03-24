package com.bi.element.controller;

import com.bi.element.domain.po.Item;
import com.bi.element.domain.validation.Add;
import com.bi.element.domain.validation.Update;
import com.bi.element.domain.vo.ItemVO;
import com.bi.element.response.R;
import com.bi.element.service.ItemService;
import com.bi.element.utils.SecurityUtils;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("element")
@AllArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("{date}")
    @ResponseBody
    public R day(@PathParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        ItemVO itemVO = new ItemVO();
        itemVO.setOwnerId(SecurityUtils.getUserId());
        List<Item> nowItem = itemService.getItem(itemVO);
        return nowItem != null ? R.ok("获取成功", nowItem) : R.error("获取失败");
    }

    @GetMapping("weakElement")
    @ResponseBody
    public R weakElement(Long elementID) {
        return itemService.deleteItem(elementID) ? R.ok() : R.error();
    }

    @PostMapping("add")
    @ResponseBody
    public R add(@Validated(Add.class) @RequestBody ItemVO item) {
        return itemService.addItem(item) ? R.ok() : R.error();
    }

    @PostMapping("change")
    @ResponseBody
    public R change(@Validated(Update.class) @RequestBody ItemVO item) {
        return itemService.changeItem(item) ? R.ok() : R.error();
    }
}
