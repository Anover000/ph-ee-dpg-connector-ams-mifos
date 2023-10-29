package org.mifos.connector.ams.utils;

import static org.mifos.connector.conductor.ConductorVariables.*;
import static org.mifos.connector.conductor.ConductorVariables.ERROR_PAYLOAD;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.HashMap;
import java.util.Map;
import org.apache.camel.Exchange;
import org.mifos.connector.ams.errorhandler.ErrorTranslator;

public class Utils {

    // use this utility method only if exchange has ERROR_CODE, ERROR_INFORMATION & ERROR_PAYLOAD variables
    public static Map<String, Object> getDefaultErrorVariable(Exchange exchange, ErrorTranslator translator)
            throws JsonProcessingException {
        Map<String, Object> variables = new HashMap<>();
        variables.put(ERROR_CODE, exchange.getProperty(ERROR_CODE));
        variables.put(ERROR_INFORMATION, exchange.getProperty(ERROR_INFORMATION));
        variables.put(ERROR_PAYLOAD, exchange.getProperty(ERROR_PAYLOAD));

        return translator.translateError(variables);
    }

}
