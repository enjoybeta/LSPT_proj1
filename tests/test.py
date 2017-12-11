#example to run
#python3 test.py "128.113.126.17:8080"

import sys
import glob, os
import json
import requests

def main(argv):
    print("num of input argument:"+str(len(argv)))
    if len(argv) == 1:
        ip = "127.0.0.1:8080"
    else:
        ip = argv[1]
    print("ip:"+ip)
    getHealth(ip)
    getStopWords(ip)
    #setTokens(ip)
    #getTokens(ip)

def getHealth(ip):
    r = requests.get("http://"+ip+"/health")
    print(r.text)

def getStopWords(ip):
    r = requests.get("http://"+ip+"/stopWords")
    print(r.text)

def setTokens(ip):
    for fileName in glob.iglob('text transformation/*.json'):
        with open(fileName, 'r') as myfile:
            dataStr=myfile.read().replace('\n', '').replace('\t', '')
        #print(dataStr)
        print("Sending setToken as POST request to "+ip)
        headers = {'Accept' : 'application/json', 'Content-Type' : 'text/plain'}
        r = requests.post("http://"+ip+"/setToken", data=dataStr,headers=headers)
        print(r.text)
        
def getTokens(ip):
    for fileName in glob.iglob('ranking/*.json'):
        with open(fileName, 'r') as myfile:
            dataStr=myfile.read().replace('\n', '').replace('\t', '')
        #print(dataStr)
        print("Sending getToken as POST request to "+ip)
        headers = {'Accept' : 'application/json', 'Content-Type' : 'text/plain'}
        r = requests.post("http://"+ip+"/getToken", data=dataStr,headers=headers)
        print(r.text)
        
if __name__ == "__main__":
    main(sys.argv)
