package org.catools.ws.soap;

import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

/**
 * Very basic implementation to be used as a base for any other project specific soap client. Each
 * project has is own parameters for envelop which cannot be generalize so use decorateEnvelope to
 * ensure that you set all parameters right.
 */
@Slf4j
public abstract class CSoapClient {
  private String url;

  public CSoapClient(String url) {
    this.url = url;
  }

  public <T, V> T call(JAXBElement<V> request, Class responseClazz) {
    try {
      // Build Soap Connection
      SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
      SOAPConnection soapConnection = soapConnectionFactory.createConnection();

      // Create request and send it to server
      SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(request), url);
      soapConnection.close();

      String response = logResponseForDebuggingPurpose(soapResponse);

      JAXBContext jc = JAXBContext.newInstance(responseClazz);
      Unmarshaller unmarshaller = jc.createUnmarshaller();
      try {
        return (T) unmarshaller.unmarshal(new StringReader(response));
      } catch (Throwable t) {
        log.error("Unexpected response from server: " + response);
        throw t;
      }
    } catch (Throwable t) {
      throw new RuntimeException(t);
    }
  }

  private String logResponseForDebuggingPurpose(SOAPMessage soapResponse)
      throws SOAPException, IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    soapResponse.writeTo(outputStream);
    String response = new String(outputStream.toByteArray());
    log.trace("Response ::>> " + response);
    return response;
  }

  private <V> SOAPMessage createSOAPRequest(JAXBElement<V> request) {
    try {
      // Create message
      MessageFactory messageFactory = MessageFactory.newInstance();
      SOAPMessage soapMessage = messageFactory.createMessage();

      // Get Soap Envelop
      SOAPPart soapPart = soapMessage.getSOAPPart();
      SOAPEnvelope envelope = decorateEnvelope(soapPart.getEnvelope());

      // Add Request to envelop
      JAXBContext jaxbContext = JAXBContext.newInstance(request.getDeclaredType());
      jaxbContext.createMarshaller().marshal(request, envelope.getBody());

      soapMessage.saveChanges();
      logRequestForDebuggingPurpose(soapMessage);
      return soapMessage;
    } catch (Throwable t) {
      throw new RuntimeException(t);
    }
  }

  private void logRequestForDebuggingPurpose(SOAPMessage soapMessage)
      throws SOAPException, IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    soapMessage.writeTo(outputStream);
    log.trace("Request ::>> " + new String(outputStream.toByteArray()));
  }

  protected abstract SOAPEnvelope decorateEnvelope(SOAPEnvelope envelope);
}
