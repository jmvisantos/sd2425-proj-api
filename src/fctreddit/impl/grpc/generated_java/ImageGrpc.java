package fctreddit.impl.grpc.generated_java;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: src/fctreddit/api/grpc/Image.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ImageGrpc {

  private ImageGrpc() {}

  public static final java.lang.String SERVICE_NAME = "Image";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<fctreddit.impl.grpc.generated_java.ImageProtoBuf.CreateImageArgs,
      fctreddit.impl.grpc.generated_java.ImageProtoBuf.CreateImageResult> getCreateImageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "createImage",
      requestType = fctreddit.impl.grpc.generated_java.ImageProtoBuf.CreateImageArgs.class,
      responseType = fctreddit.impl.grpc.generated_java.ImageProtoBuf.CreateImageResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<fctreddit.impl.grpc.generated_java.ImageProtoBuf.CreateImageArgs,
      fctreddit.impl.grpc.generated_java.ImageProtoBuf.CreateImageResult> getCreateImageMethod() {
    io.grpc.MethodDescriptor<fctreddit.impl.grpc.generated_java.ImageProtoBuf.CreateImageArgs, fctreddit.impl.grpc.generated_java.ImageProtoBuf.CreateImageResult> getCreateImageMethod;
    if ((getCreateImageMethod = ImageGrpc.getCreateImageMethod) == null) {
      synchronized (ImageGrpc.class) {
        if ((getCreateImageMethod = ImageGrpc.getCreateImageMethod) == null) {
          ImageGrpc.getCreateImageMethod = getCreateImageMethod =
              io.grpc.MethodDescriptor.<fctreddit.impl.grpc.generated_java.ImageProtoBuf.CreateImageArgs, fctreddit.impl.grpc.generated_java.ImageProtoBuf.CreateImageResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "createImage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fctreddit.impl.grpc.generated_java.ImageProtoBuf.CreateImageArgs.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fctreddit.impl.grpc.generated_java.ImageProtoBuf.CreateImageResult.getDefaultInstance()))
              .setSchemaDescriptor(new ImageMethodDescriptorSupplier("createImage"))
              .build();
        }
      }
    }
    return getCreateImageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<fctreddit.impl.grpc.generated_java.ImageProtoBuf.GetImageArgs,
      fctreddit.impl.grpc.generated_java.ImageProtoBuf.GetImageResult> getGetImageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getImage",
      requestType = fctreddit.impl.grpc.generated_java.ImageProtoBuf.GetImageArgs.class,
      responseType = fctreddit.impl.grpc.generated_java.ImageProtoBuf.GetImageResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<fctreddit.impl.grpc.generated_java.ImageProtoBuf.GetImageArgs,
      fctreddit.impl.grpc.generated_java.ImageProtoBuf.GetImageResult> getGetImageMethod() {
    io.grpc.MethodDescriptor<fctreddit.impl.grpc.generated_java.ImageProtoBuf.GetImageArgs, fctreddit.impl.grpc.generated_java.ImageProtoBuf.GetImageResult> getGetImageMethod;
    if ((getGetImageMethod = ImageGrpc.getGetImageMethod) == null) {
      synchronized (ImageGrpc.class) {
        if ((getGetImageMethod = ImageGrpc.getGetImageMethod) == null) {
          ImageGrpc.getGetImageMethod = getGetImageMethod =
              io.grpc.MethodDescriptor.<fctreddit.impl.grpc.generated_java.ImageProtoBuf.GetImageArgs, fctreddit.impl.grpc.generated_java.ImageProtoBuf.GetImageResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getImage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fctreddit.impl.grpc.generated_java.ImageProtoBuf.GetImageArgs.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fctreddit.impl.grpc.generated_java.ImageProtoBuf.GetImageResult.getDefaultInstance()))
              .setSchemaDescriptor(new ImageMethodDescriptorSupplier("getImage"))
              .build();
        }
      }
    }
    return getGetImageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<fctreddit.impl.grpc.generated_java.ImageProtoBuf.DeleteImageArgs,
      fctreddit.impl.grpc.generated_java.ImageProtoBuf.DeleteImageResult> getDeleteImageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "deleteImage",
      requestType = fctreddit.impl.grpc.generated_java.ImageProtoBuf.DeleteImageArgs.class,
      responseType = fctreddit.impl.grpc.generated_java.ImageProtoBuf.DeleteImageResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<fctreddit.impl.grpc.generated_java.ImageProtoBuf.DeleteImageArgs,
      fctreddit.impl.grpc.generated_java.ImageProtoBuf.DeleteImageResult> getDeleteImageMethod() {
    io.grpc.MethodDescriptor<fctreddit.impl.grpc.generated_java.ImageProtoBuf.DeleteImageArgs, fctreddit.impl.grpc.generated_java.ImageProtoBuf.DeleteImageResult> getDeleteImageMethod;
    if ((getDeleteImageMethod = ImageGrpc.getDeleteImageMethod) == null) {
      synchronized (ImageGrpc.class) {
        if ((getDeleteImageMethod = ImageGrpc.getDeleteImageMethod) == null) {
          ImageGrpc.getDeleteImageMethod = getDeleteImageMethod =
              io.grpc.MethodDescriptor.<fctreddit.impl.grpc.generated_java.ImageProtoBuf.DeleteImageArgs, fctreddit.impl.grpc.generated_java.ImageProtoBuf.DeleteImageResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "deleteImage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fctreddit.impl.grpc.generated_java.ImageProtoBuf.DeleteImageArgs.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fctreddit.impl.grpc.generated_java.ImageProtoBuf.DeleteImageResult.getDefaultInstance()))
              .setSchemaDescriptor(new ImageMethodDescriptorSupplier("deleteImage"))
              .build();
        }
      }
    }
    return getDeleteImageMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ImageStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ImageStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ImageStub>() {
        @java.lang.Override
        public ImageStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ImageStub(channel, callOptions);
        }
      };
    return ImageStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ImageBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ImageBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ImageBlockingStub>() {
        @java.lang.Override
        public ImageBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ImageBlockingStub(channel, callOptions);
        }
      };
    return ImageBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ImageFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ImageFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ImageFutureStub>() {
        @java.lang.Override
        public ImageFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ImageFutureStub(channel, callOptions);
        }
      };
    return ImageFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void createImage(fctreddit.impl.grpc.generated_java.ImageProtoBuf.CreateImageArgs request,
        io.grpc.stub.StreamObserver<fctreddit.impl.grpc.generated_java.ImageProtoBuf.CreateImageResult> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateImageMethod(), responseObserver);
    }

    /**
     */
    default void getImage(fctreddit.impl.grpc.generated_java.ImageProtoBuf.GetImageArgs request,
        io.grpc.stub.StreamObserver<fctreddit.impl.grpc.generated_java.ImageProtoBuf.GetImageResult> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetImageMethod(), responseObserver);
    }

    /**
     */
    default void deleteImage(fctreddit.impl.grpc.generated_java.ImageProtoBuf.DeleteImageArgs request,
        io.grpc.stub.StreamObserver<fctreddit.impl.grpc.generated_java.ImageProtoBuf.DeleteImageResult> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteImageMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Image.
   */
  public static abstract class ImageImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ImageGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Image.
   */
  public static final class ImageStub
      extends io.grpc.stub.AbstractAsyncStub<ImageStub> {
    private ImageStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ImageStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ImageStub(channel, callOptions);
    }

    /**
     */
    public void createImage(fctreddit.impl.grpc.generated_java.ImageProtoBuf.CreateImageArgs request,
        io.grpc.stub.StreamObserver<fctreddit.impl.grpc.generated_java.ImageProtoBuf.CreateImageResult> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateImageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getImage(fctreddit.impl.grpc.generated_java.ImageProtoBuf.GetImageArgs request,
        io.grpc.stub.StreamObserver<fctreddit.impl.grpc.generated_java.ImageProtoBuf.GetImageResult> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetImageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteImage(fctreddit.impl.grpc.generated_java.ImageProtoBuf.DeleteImageArgs request,
        io.grpc.stub.StreamObserver<fctreddit.impl.grpc.generated_java.ImageProtoBuf.DeleteImageResult> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteImageMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Image.
   */
  public static final class ImageBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ImageBlockingStub> {
    private ImageBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ImageBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ImageBlockingStub(channel, callOptions);
    }

    /**
     */
    public fctreddit.impl.grpc.generated_java.ImageProtoBuf.CreateImageResult createImage(fctreddit.impl.grpc.generated_java.ImageProtoBuf.CreateImageArgs request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateImageMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<fctreddit.impl.grpc.generated_java.ImageProtoBuf.GetImageResult> getImage(
        fctreddit.impl.grpc.generated_java.ImageProtoBuf.GetImageArgs request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetImageMethod(), getCallOptions(), request);
    }

    /**
     */
    public fctreddit.impl.grpc.generated_java.ImageProtoBuf.DeleteImageResult deleteImage(fctreddit.impl.grpc.generated_java.ImageProtoBuf.DeleteImageArgs request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteImageMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Image.
   */
  public static final class ImageFutureStub
      extends io.grpc.stub.AbstractFutureStub<ImageFutureStub> {
    private ImageFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ImageFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ImageFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<fctreddit.impl.grpc.generated_java.ImageProtoBuf.CreateImageResult> createImage(
        fctreddit.impl.grpc.generated_java.ImageProtoBuf.CreateImageArgs request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateImageMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<fctreddit.impl.grpc.generated_java.ImageProtoBuf.DeleteImageResult> deleteImage(
        fctreddit.impl.grpc.generated_java.ImageProtoBuf.DeleteImageArgs request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteImageMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_IMAGE = 0;
  private static final int METHODID_GET_IMAGE = 1;
  private static final int METHODID_DELETE_IMAGE = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_IMAGE:
          serviceImpl.createImage((fctreddit.impl.grpc.generated_java.ImageProtoBuf.CreateImageArgs) request,
              (io.grpc.stub.StreamObserver<fctreddit.impl.grpc.generated_java.ImageProtoBuf.CreateImageResult>) responseObserver);
          break;
        case METHODID_GET_IMAGE:
          serviceImpl.getImage((fctreddit.impl.grpc.generated_java.ImageProtoBuf.GetImageArgs) request,
              (io.grpc.stub.StreamObserver<fctreddit.impl.grpc.generated_java.ImageProtoBuf.GetImageResult>) responseObserver);
          break;
        case METHODID_DELETE_IMAGE:
          serviceImpl.deleteImage((fctreddit.impl.grpc.generated_java.ImageProtoBuf.DeleteImageArgs) request,
              (io.grpc.stub.StreamObserver<fctreddit.impl.grpc.generated_java.ImageProtoBuf.DeleteImageResult>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getCreateImageMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              fctreddit.impl.grpc.generated_java.ImageProtoBuf.CreateImageArgs,
              fctreddit.impl.grpc.generated_java.ImageProtoBuf.CreateImageResult>(
                service, METHODID_CREATE_IMAGE)))
        .addMethod(
          getGetImageMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              fctreddit.impl.grpc.generated_java.ImageProtoBuf.GetImageArgs,
              fctreddit.impl.grpc.generated_java.ImageProtoBuf.GetImageResult>(
                service, METHODID_GET_IMAGE)))
        .addMethod(
          getDeleteImageMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              fctreddit.impl.grpc.generated_java.ImageProtoBuf.DeleteImageArgs,
              fctreddit.impl.grpc.generated_java.ImageProtoBuf.DeleteImageResult>(
                service, METHODID_DELETE_IMAGE)))
        .build();
  }

  private static abstract class ImageBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ImageBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return fctreddit.impl.grpc.generated_java.ImageProtoBuf.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Image");
    }
  }

  private static final class ImageFileDescriptorSupplier
      extends ImageBaseDescriptorSupplier {
    ImageFileDescriptorSupplier() {}
  }

  private static final class ImageMethodDescriptorSupplier
      extends ImageBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ImageMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ImageGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ImageFileDescriptorSupplier())
              .addMethod(getCreateImageMethod())
              .addMethod(getGetImageMethod())
              .addMethod(getDeleteImageMethod())
              .build();
        }
      }
    }
    return result;
  }
}
