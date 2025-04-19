package fctreddit.impl.server.grpc;

import com.google.protobuf.ByteString;

import fctreddit.api.java.Image;
import fctreddit.api.java.Result;
import fctreddit.impl.grpc.generated_java.ImageProtoBuf.CreateImageArgs;
import fctreddit.impl.grpc.generated_java.ImageProtoBuf.CreateImageResult;
import fctreddit.impl.grpc.generated_java.ImageProtoBuf.DeleteImageArgs;
import fctreddit.impl.grpc.generated_java.ImageProtoBuf.DeleteImageResult;
import fctreddit.impl.grpc.generated_java.ImageProtoBuf.GetImageArgs;
import fctreddit.impl.grpc.generated_java.ImageProtoBuf.GetImageResult;
import fctreddit.impl.grpc.generated_java.UsersGrpc;
import fctreddit.impl.server.java.JavaImage;
import io.grpc.BindableService;
import io.grpc.ServerServiceDefinition;
import io.grpc.stub.StreamObserver;

public class GrpcImageServerStub implements UsersGrpc.AsyncService, BindableService{

  Image impl = new JavaImage();

  @Override
  public ServerServiceDefinition bindService() {
    return UsersGrpc.bindService(this);
  }


  public void createImage(CreateImageArgs request, StreamObserver<CreateImageResult> responseObserver) {
    Result<String> res = impl.createImage(request.getUserId(), request.getImageContents().toByteArray(), request.getPassword());
    if (!res.isOK()) {
      responseObserver.onError(errorCodeToStatus(res.error()));
    } else {
      CreateImageResult result = CreateImageResult.newBuilder().setImageId(res.value()).build();
      responseObserver.onNext(result);
      responseObserver.onCompleted();
    }
  }

  public void getImage(GetImageArgs request, StreamObserver<GetImageResult> responseObserver) {
    Result<byte[]> res = impl.getImage(request.getUserId(), request.getImageId());
    if (!res.isOK()) {
        responseObserver.onError(errorCodeToStatus(res.error()));
    }

}

  public void deleteImage(DeleteImageArgs request, StreamObserver<DeleteImageResult> responseObserver) {
    Result<Void> res = impl.deleteImage(request.getUserId(), request.getImageId(), request.getPassword());
    if (!res.isOK()) {
      responseObserver.onError(errorCodeToStatus(res.error()));
    } else {
      DeleteImageResult result = DeleteImageResult.newBuilder().build();
      responseObserver.onNext(result);
      responseObserver.onCompleted();
    }
  }

	protected static Throwable errorCodeToStatus(Result.ErrorCode error) {
		var status = switch (error) {
			case NOT_FOUND -> io.grpc.Status.NOT_FOUND;
			case CONFLICT -> io.grpc.Status.ALREADY_EXISTS;
			case FORBIDDEN -> io.grpc.Status.PERMISSION_DENIED;
			case NOT_IMPLEMENTED -> io.grpc.Status.UNIMPLEMENTED;
			case BAD_REQUEST -> io.grpc.Status.INVALID_ARGUMENT;
			default -> io.grpc.Status.INTERNAL;
		};

		return status.asException();
	}
}
