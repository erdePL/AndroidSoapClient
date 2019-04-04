package com.example.kamil.androidsoapclient2.xmlSoapRequests;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Date;

/**
 * Created by Kamil on 04/04/2019.
 */

public class AddMessageXmlSoapRequest {
    private Envelope envelope;
    public AddMessageXmlSoapRequest(String author, Date creationDate, long id, String messageContent){
        this.envelope = new Envelope(author, creationDate, id, messageContent);
    }

    @Root
    public class Envelope{
        @Element
        private Body body;
        public Envelope(String author, Date creationDate, long id, String messageContent){
            this.body = new Body(author, creationDate, id, messageContent);
        }

        public class Body {
            @Element
            private AddMessage addMessage;
            public Body(String author, Date creationDate, long id, String messageContent) {
                this.addMessage = new AddMessage(author, creationDate, id, messageContent);
            }

            public class AddMessage {
                @Element
                private Arg0 arg0;
                public AddMessage(String author, Date creationDate, long id, String messageContent) {
                    this.arg0 = new Arg0(author, creationDate, id, messageContent);
                }

                public class Arg0 {
                    @Element
                    private String author;
                    @Element
                    private Date creationDate;
                    @Element
                    private long id;
                    @Element
                    private String messageContent;
                    public Arg0(String author, Date creationDate, long id, String messageContent) {
                        this.author = author;
                        this.creationDate = creationDate;
                        this.id = id;
                        this.messageContent = messageContent;
                    }
                }
            }
        }
    }
}

