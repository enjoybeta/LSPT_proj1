import sys
import glob, os
import json
import requests

def main(argv):
    for fileName in glob.iglob('text transformation/*.json'):
        with open(fileName, 'r') as myfile:
            dataStr=myfile.read().replace('\n', '')
        print(dataStr)
        r = requests.get("http://127.0.0.1:8080/stopWords")
        print(r.text)
        #result = requests.post("127.0.0.1/setToken", data=dataStr)
        break

if __name__ == "__main__":
    main(sys.argv)