@Grab(group = 'org.codehaus.groovy.modules.http-builder', module = 'http-builder', version = '0.7')
@Grab(group = 'org.apache.httpcomponents', module = 'httpclient', version = '4.5.11')
@Grab(group = 'org.codehaus.groovy.modules.http-builder', module = 'http-builder', version = '0.7')
@Grab(group = 'org.apache.httpcomponents', module = 'httpclient', version = '4.5.11')
import groovyx.net.http.HTTPBuilder

import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocket
import java.security.SecureRandom

import static groovyx.net.http.Method.GET

def requestURL = ""
def http = new HTTPBuilder( requestURL)

def sslProtocols = ['TLSv1.2']

println "iniciando script pra url ${requestURL}"

def sslContext = SSLContext.getInstance("TLS")
println "sslContext ${sslContext}"
sslContext.init(null, null, new SecureRandom())

def sf = new org.apache.http.conn.ssl.SSLSocketFactory(sslContext) {

    protected void prepareSocket(final SSLSocket socket) throws IOException {
        if (sslProtocols) {

            socket.setEnabledProtocols(sslProtocols as String[])
        }
    }
}

http.client.connectionManager.schemeRegistry.register(
        new org.apache.http.conn.scheme.Scheme("https", sf, 443)
)

def status = http.request( GET ) {

    response.success = { res, reader ->
	println "reader: ${reader}"
        println "response: ${res}" 
    }
//    println "status ${statuis}"
}
