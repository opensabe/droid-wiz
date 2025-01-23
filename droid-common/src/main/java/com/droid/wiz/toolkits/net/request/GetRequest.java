package com.droid.wiz.toolkits.net.request;

import okhttp3.Request;

public class GetRequest extends BaseRequest<GetRequest>{
  @Override
  protected void handleRequest(Request.Builder builder) {
    builder.get();
  }
}
