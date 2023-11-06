package com.jatin.splunk

class SplunkLogs implements Serializable {

    Static Map forwardLogs(String HEC, String host, String source, String index, Map fields, List eventData){
        String jsonBody = ""
        for( def event : eventData){
            def body = [:]
            body["host"]    = host
            body["source"]  = source
            body["index"]   = index
            body["fields"]  = fields
            body["event"]   = event
            jsonBody += writeJSON returnText: true, json: body 
        }
        Map req = [
            url: 'https://splunk-indexer.splunk:8088/services/collector',
            //authentication: 'SWB_USER',
            consoleLogResponseBody: false,
            contentType:  'APPLICATION_JSON',
            customHeaders: [[name:'Authorization', value: "Splunk ${HEC}"]],
            httpMode:'POST',
            ignoreSslErrors: true,
            quiet: false,
            requestBody: jsonBody,
            timeout: '2m',
            validResponseCodes: '200:399', // 400 is already a problem
            //validResponseContent: null
        ]
        def response = httpRequest(req) 
        return response
    }

    Static Map computeJPLogs(){
        
    }
}


