import sys
import glob, os
import json
import requests

def main(argv):
    #getHealth()
    #getStopWords()
    setTokens()

def getHealth():
    r = requests.get("http://127.0.0.1:8080/health")
    print(r.text)

def getStopWords():
    r = requests.get("http://127.0.0.1:8080/stopWords")
    print(r.text)

def setTokens():
    for fileName in glob.iglob('text transformation/*.json'):
        with open(fileName, 'r') as myfile:
            dataStr=myfile.read().replace('\n', '').replace('\t', '')
        print(dataStr)
        headers = {'Accept' : 'application/json', 'Content-Type' : 'text/plain'}
        r = requests.post("http://127.0.0.1:8080/setToken", data=dataStr,headers=headers)
        print(r.text)
        
if __name__ == "__main__":
    main(sys.argv)