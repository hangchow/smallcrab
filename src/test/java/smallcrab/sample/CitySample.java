package smallcrab.sample;

import java.io.IOException;

public class CitySample {

	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println(GetAliyunAddressMapper.getAddress("4.9E-324", "4.9E-324"));
	}

}
