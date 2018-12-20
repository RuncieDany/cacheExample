package com.n26;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomDateSerializer extends JsonSerializer<Instant> {
	
	@Override
	public void serialize(Instant instant, JsonGenerator gen, SerializerProvider arg2) throws IOException {
		SimpleDateFormat formatter= new SimpleDateFormat("YYYY-MM-DDThh:mm:ss.sssZ");
		String formattedDate=formatter.format(instant);
		gen.writeString(formattedDate);
		
	}

}
