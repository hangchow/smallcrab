package smallcrab.sample;

import java.io.IOException;
import java.util.Arrays;

public class CitySample2 {

	public static void main(String[] args) throws InterruptedException, IOException {
		String[] adds = GetAliyunAddressMapper.getAddress("120.116899", "30.276709");
		System.out.println(Arrays.toString(adds));
	}

}
