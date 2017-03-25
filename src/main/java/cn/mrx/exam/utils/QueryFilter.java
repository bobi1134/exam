package cn.mrx.exam.utils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

/**
 *
 * @author Mr.X
 * @Date 2017-01-25
 */
public class QueryFilter {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private HttpServletRequest request;

    private List<ParamEntity> paramEntities = new ArrayList<ParamEntity>();

    /**
     *
     * @param request
     */
    private QueryFilter(HttpServletRequest request) {
        this.request = request;
    }

    /**
     *
     * @param request
     * @return
     */
    public static QueryFilter getInstance(HttpServletRequest request) {
        return new QueryFilter(request);
    }

    /**
     *
     * @param <T>
     * @return
     */
    public <T> EntityWrapper buildEntityWrapper() {
        EntityWrapper<T> entityWrapper = new EntityWrapper();
        //步骤1，解析传过来的request：parseRequest()
        List<ParamEntity> paramEntities = parseRequest();
        for (ParamEntity paramEntity : paramEntities) {
            logger.info("[url="+request.getRequestURI()+",paramEntityName="+paramEntity.getName()+",paramEntityValue="+paramEntity.getValue()+"]\n");
            entityWrapper.addFilter(paramEntity.getName(), paramEntity.getValue());
        }
        return entityWrapper;
    }

    /**
     *
     * @return
     */
    private List<ParamEntity> parseRequest() {
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            //步骤2，添加过滤条件
            addFilter(paramName, paramValue);
        }
        return paramEntities;
    }

    /**
     *
     * @param paramName
     * @param paramValue
     */
    private void addFilter(String paramName, String paramValue) {
        logger.info("[url="+request.getRequestURI()+",paramName="+paramName+"]\n");
        if (!paramName.startsWith("QUERY-") || !(paramValue.length() > 0)) {
            return;
        }

        String[] filterParam = paramName.split("-");

        if (filterParam.length != 4) {
            logger.info("查询参数长度不够！paramName："+paramName);
            return;
        }

        String columnName = filterParam[1];
        String columnType = filterParam[2];
        String operator = filterParam[3];
        String sqlOperator = getSqlOperator(operator);

        //步骤3，转换SQL操作符
        String filter = columnName + sqlOperator + "{0}";
        //步骤4，将String值转换成Object，用于拼写HQL，替换操作符和值
        Object objValue = getObjValue(columnType, operator, paramValue);

        //返回参数！
        paramEntities.add(new ParamEntity(filter, objValue));
    }

    /**
     * 转换SQL操作符
     *
     * @param operator
     * @return
     */
    private String getSqlOperator(String operator) {
        if (StringUtils.equalsIgnoreCase(operator, "EQ")) {
            return " = ";
        }
        if (StringUtils.equalsIgnoreCase(operator, "NE")) {
            return " != ";
        }
        if (StringUtils.equalsIgnoreCase(operator, "LT")) {
            return " < ";
        }
        if (StringUtils.equalsIgnoreCase(operator, "GT")) {
            return " > ";
        }
        if (StringUtils.equalsIgnoreCase(operator, "LE")) {
            return " <= ";
        }
        if (StringUtils.equalsIgnoreCase(operator, "GE")) {
            return " >= ";
        }
        if (StringUtils.equalsIgnoreCase(operator, "IN")) {
            return " in ";
        }
        if (StringUtils.equalsIgnoreCase(operator, "LK")
                || StringUtils.equalsIgnoreCase(operator, "RLK")
                || StringUtils.equalsIgnoreCase(operator, "LLK")) {
            return " like ";
        }
        if (StringUtils.equalsIgnoreCase(operator, "NLK")) {
            return " not like ";
        }
        return "";
    }

    /**
     * 将String值转换成Object，用于拼写HQL，替换操作符和值
     * S:String L:Long I:Integer D:Date ST:Short BD:BigDecimal FT:Float
     *
     * @param columnType
     * @param operator
     * @param value
     * @return
     */
    private Object getObjValue(String columnType, String operator, String value) {
        if (StringUtils.equalsIgnoreCase(columnType, "S")) {
            if (StringUtils.equalsIgnoreCase(operator, "LK")) {
                value = "'%" + value + "%'";
            } else if (StringUtils.equalsIgnoreCase(operator, "RLK")) {
                value = value + "%";
            } else if (StringUtils.equalsIgnoreCase(operator, "LLK")) {
                value = "%" + value;
            }
            return value;
        }
        if (StringUtils.equalsIgnoreCase(columnType, "L")) {
            return Long.parseLong(value);
        }
        if (StringUtils.equalsIgnoreCase(columnType, "I")) {
            return Integer.parseInt(value);
        }
        if (StringUtils.equalsIgnoreCase(columnType, "D")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = DateUtils.parseDate(value, new String[]{
                        "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "yyyy-MM-dd HH",
                        "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM/dd", "yyyy/MM/dd HH"});
            } catch (ParseException e) {
                logger.info("时间转化异常！"+e.getMessage());
                e.printStackTrace();
                return sdf.format(new Date());
            }
            return sdf.format(date);
        }
        return null;
    }

}
