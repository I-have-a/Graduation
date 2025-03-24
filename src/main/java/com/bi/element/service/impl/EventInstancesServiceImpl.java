package com.bi.element.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bi.element.domain.po.EventInstance;
import com.bi.element.domain.po.Item;
import com.bi.element.domain.po.RecurrenceRule;
import com.bi.element.mapper.EventInstanceMapper;
import com.bi.element.service.EventInstancesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EventInstancesServiceImpl extends ServiceImpl<EventInstanceMapper, EventInstance> implements EventInstancesService {

    private final EventInstanceMapper eventInstanceMapper;

    public void createRecurringEvent(Item event, RecurrenceRule rule) {
        List<LocalDate> dates = generateRecurringDates(rule, event.getEventDate());
        for (LocalDate date : dates) {
            EventInstance instance = new EventInstance();
            instance.setItemId(event.getId());
            instance.setEventDate(date);
            eventInstanceMapper.insert(instance);
        }
    }

    private List<LocalDate> generateRecurringDates(RecurrenceRule rule, LocalDate date) {
        // 根据规则生成日期列表（如每天、每周等）
        // 示例代码省略，需根据具体规则实现
        List<LocalDate> dates = new ArrayList<>();
        return dates;
    }
}
