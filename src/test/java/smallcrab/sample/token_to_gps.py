# encoding=utf-8
import urllib2
import json
token_gps_url = "http://localhost:8082/gps/real"
def get_address(lon,lat):
    address_url = "http://gc.ditu.aliyun.com/regeocoding?l="+str(lon)+","+str(lat)
    request = urllib2.urlopen(address_url).read().decode("utf-8");
    request = json.loads(request)
    address_name = byteify(request)["addrList"][1]['admName']
    local_list = address_name.split(",");
    address = [None] * 3
    if len(local_list) == 2:
        address[0] = local_list[0]
        address[1] = local_list[1]
        address[2] = local_list[1]
    if len(local_list) == 1:
        address[0] = local_list[0]
        address[1] = local_list[0]
        address[2] = local_list[0]
    if len(local_list) >= 3:
        address[0] = local_list[0]
        address[1] = local_list[1]
        address[2] = local_list[2]
    return address

def get_lat_long(token):
    data = {}
    try:
        token_url = token_gps_url+"?_token="+token
        request = urllib2.urlopen(token_url).read().decode("utf-8");
        # request = '''
        # {"timestamp":"1432278304","uid":"20806444","succ":true,"token":"f68cdf655af1370d15304103334d9fa8","data":{"latitude":"30.276709","longitude":"120.116899","timestamp":"1432271941000"},"_force":false,"code":"0"}
        # '''
        data = byteify(json.loads(request))["data"]
    except:
        pass
    return data

def byteify(input):
    if isinstance(input, dict):
        result = {}
        for key,value in input.iteritems():
            result[byteify(key)] = byteify(value)
        return result
    elif isinstance(input, list):
        result = []
        for element in input:
            result.append(byteify(element))
        return result
    elif isinstance(input, unicode):
        return input.encode('utf-8')
    else:
        return input
def run():
    input_token = "./token.txt"
    output_file ="./output.csv"
    input_file = open(input_token)
    output_file = open(output_file,"a+")
    for line in input_file:
        line = line.replace("\n","")
        gps_data = get_lat_long(line)
        if len(gps_data) != 0:
            city = get_address(gps_data["latitude"],gps_data["longitude"])
            write_list = [line,gps_data["longitude"],gps_data["latitude"],gps_data["timestamp"]]
            write_list.extend(city)
            print(write_list)
            output_file.write(",".join(write_list)+"\n")
    input_file.close()
    output_file.close()

if __name__ == "__main__":
    run()