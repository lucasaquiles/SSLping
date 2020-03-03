@Grab(group = 'org.codehaus.groovy.modules.http-builder', module = 'http-builder', version = '0.7')
@Grab(group = 'org.apache.httpcomponents', module = 'httpclient', version = '4.5.11')
import groovyx.net.http.HTTPBuilder
@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7' )
@Grab(group='org.apache.httpcomponents', module='httpclient', version='4.5.11')


import groovyx.net.http.HTTPBuilder

import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocket
import java.security.SecureRandom

import static groovyx.net.http.Method.HEAD

def requestURL = "<HOST>"
def http = new HTTPBuilder( requestURL)

def sslProtocols = ['TLSv1', 'TLSv1.1', 'TLSv1.2']

println "iniciando script pra url ${requestURL}"


def sslContext = SSLContext.getInstance("TLS")
sslContext.init(null, null, new SecureRandom())

def sf = new org.apache.http.conn.ssl.SSLSocketFactory(sslContext) {

    protected void prepareSocket(final SSLSocket socket) throws IOException {
        if (sslProtocols) {

            socket.setEnabledProtocols(['TLSv1', 'TLSv1.1', 'TLSv1.2'] as String[])
        }
    }
}

http.client.connectionManager.schemeRegistry.register(
        new org.apache.http.conn.scheme.Scheme("https", sf, 443)
)

def status = http.request( HEAD ) {

    println "status ---"
    response.success = { it.status }
}