package com.bi.element.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bi.element.domain.po.RecurrenceRule;
import com.bi.element.mapper.RecurrenceRuleMapper;
import com.bi.element.service.RecurrenceRuleService;
import org.springframework.stereotype.Service;

@Service
public class RecurrenceRuleServiceImpl extends ServiceImpl<RecurrenceRuleMapper, RecurrenceRule> implements RecurrenceRuleService {
}
