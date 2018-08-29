package com.springRest.parser;

import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v21.message.ACK;
import ca.uhn.hl7v2.parser.DefaultXMLParser;
import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hl7v2.parser.XMLParser;

public class ParserDemo {

	public static void main(String args[]) {
		// for demo purposes, we just declare a literal message string
		final String ackMessageString = "MSH|^~\\&|foo|foo||foo|200108151718||ACK^A01^ACK|1|D|2.4|\rMSA|AA\r"
				+ "EVN||200803051509||||200803031508\r"
				+ "PID|1||PATID1239^^^NIST MPI^MR||Smirnoff^Peggy^^^^^M||19750401|F||2106-3^White^HL70005^wh^white^L\r"
				+ "ORC|RE|ORD448811^NIST EHR|R-511^NIST Lab Filler|||||||||1234567890^Fine^Larry^^^Dr.^^^NIST-AA-1^L^^^NPI\r";
		// instantiate a PipeParser, which handles the "traditional encoding"
		final PipeParser pipeParser = new PipeParser();

		try {
			// parse the message string into a Message object
			final Message message = pipeParser.parse(ackMessageString);

			// if it is an ACK message (as we know it is), cast it to an
			// ACK object so that it is easier to work with, and change a value
			if (message instanceof ACK) {
				final ACK ack = (ACK) message;
				ack.getMSH().getPROCESSINGID().setValue("p");// getProcessingID().getProcessingMode().setValue("P")

			}

			// instantiate an XML parser
			final XMLParser xmlParser = new DefaultXMLParser();

			// encode message in XML
			final String ackMessageInXML = xmlParser.encode(message);
			/*
			 * final RestTemplate restTemplate = new RestTemplate(); final
			 * UserID userId = new UserID(); userId.setUser_id("PATID1239");
			 * final HttpEntity<UserID> request = new HttpEntity<>(userId);
			 * final ResponseEntity<UserIDResponse> response =
			 * restTemplate.exchange(
			 * "http://18.188.253.202:3000/api/org.orcicare.network.business.CreateOrciMember",
			 * HttpMethod.POST, request, UserIDResponse.class);
			 */
			// print XML-encoded message to standard out
			System.out.println(ackMessageInXML);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}