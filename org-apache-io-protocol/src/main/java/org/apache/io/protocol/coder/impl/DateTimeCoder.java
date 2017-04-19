package org.apache.io.protocol.coder.impl;

import net.sf.cglib.beans.BeanMap;
import org.apache.io.protocol.annotation.DateTime;
import org.apache.io.protocol.coder.ICoder;
import org.apache.io.protocol.core.BitBuffer;
import org.apache.io.protocol.utils.ByteBufferUtil;
import org.apache.io.protocol.utils.HexStringUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @Project:net-top-framwork-protocol
 * @Package net.top.framwork.protocolpool.coder
 * @Description: 2016-11-25 09:16:00  <-> 10 0b 19 09 01 00
 * @author: xsy
 * @date： 2016/11/25
 * @version： V1.0
 */
public class DateTimeCoder implements ICoder {

    public void decode(BitBuffer bitBuffer, BeanMap beanMap, Field field, Annotation annotation) throws Exception {
        DateTime dateTime = (DateTime) annotation;
        byte[] buf = bitBuffer.readBytes(new byte[6]);
        if(dateTime.isBCDCode())
            beanMap.put(field.getName(), ByteBufferUtil.bcdbuf2Date(buf));
        else
            beanMap.put(field.getName(), ByteBufferUtil.buf2Date(buf));
    }

    public void encode(BitBuffer bitBuffer, BeanMap beanMap, Field field, Annotation annotation) throws Exception {
        DateTime dateTime = (DateTime) annotation;
        long millSec = (Long) beanMap.get(field.getName());
        if(dateTime.isBCDCode())
            bitBuffer.write(ByteBufferUtil.date2bcdbuf(millSec));
        else
            bitBuffer.write(ByteBufferUtil.date2buf(millSec));
    }

    @Override
    public String toPrettyHexString(BitBuffer bitBuffer, BeanMap beanMap, Field field, Annotation annotation) throws Exception {
        DateTime dateTime = (DateTime) annotation;
        long millSec = (Long) beanMap.get(field.getName());
        if(dateTime.isBCDCode()) {
            return String.format(FIELD_TEMPLATE, HexStringUtil.toHexString(ByteBufferUtil.date2bcdbuf(millSec)), field.getName(), millSec);
        } else {
            return String.format(FIELD_TEMPLATE, HexStringUtil.toHexString(ByteBufferUtil.date2buf(millSec)), field.getName(), millSec);
        }
    }
}
