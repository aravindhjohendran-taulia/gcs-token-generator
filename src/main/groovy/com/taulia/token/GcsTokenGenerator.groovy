package com.taulia.token

import com.google.auth.oauth2.GoogleCredentials
import com.google.auth.oauth2.ServiceAccountCredentials

class GcsTokenGenerator {

  private String serviceAccountKeyFile = 'sa-key.json'

  String getBearerToken() {
    InputStream saFileStream = getClass().getClassLoader().getResourceAsStream("sa-key.json")
    ServiceAccountCredentials serviceAccountCredentials = ServiceAccountCredentials.fromStream(saFileStream)
    GoogleCredentials googleCredentials = serviceAccountCredentials
      .createScoped(["https://mail.google.com"])
      .createDelegated("alyssaw@inbox.taulia.com")
    Map<String, List<String>>  requestMetadata = googleCredentials.requestMetadata
    List<String> authorization = requestMetadata.get('Authorization')
    String bearerToken = authorization.get(0)
    return bearerToken
  }

  static void main(String[] args) {

    GcsTokenGenerator generator = new GcsTokenGenerator()
    String token = generator.getBearerToken()

    System.out.println(token)
  }

}
