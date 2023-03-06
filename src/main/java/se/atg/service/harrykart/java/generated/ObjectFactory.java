
package se.atg.service.harrykart.java.generated;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;

import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _HarryKart_QNAME = new QName("", "harryKart");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link HarryKartType }
     * 
     */
    public HarryKartType createHarryKartType() {
        return new HarryKartType();
    }

    /**
     * Create an instance of {@link ParticipantType }
     * 
     */
    public ParticipantType createParticipantType() {
        return new ParticipantType();
    }

    /**
     * Create an instance of {@link StartListType }
     * 
     */
    public StartListType createStartListType() {
        return new StartListType();
    }

    /**
     * Create an instance of {@link LaneType }
     * 
     */
    public LaneType createLaneType() {
        return new LaneType();
    }

    /**
     * Create an instance of {@link LoopType }
     * 
     */
    public LoopType createLoopType() {
        return new LoopType();
    }

    /**
     * Create an instance of {@link PowerUpsType }
     * 
     */
    public PowerUpsType createPowerUpsType() {
        return new PowerUpsType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HarryKartType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link HarryKartType }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "harryKart")
    public JAXBElement<HarryKartType> createHarryKart(HarryKartType value) {
        return new JAXBElement<HarryKartType>(_HarryKart_QNAME, HarryKartType.class, null, value);
    }

}
