package fctreddit.impl.server.grpc;

import java.util.List;
import java.util.logging.Logger;

import fctreddit.api.User;
import fctreddit.api.java.Result;
import fctreddit.api.java.Users;
import fctreddit.impl.grpc.generated_java.UsersGrpc;
import fctreddit.impl.grpc.generated_java.UsersProtoBuf.CreateUserArgs;
import fctreddit.impl.grpc.generated_java.UsersProtoBuf.CreateUserResult;
import fctreddit.impl.grpc.generated_java.UsersProtoBuf.DeleteUserArgs;
import fctreddit.impl.grpc.generated_java.UsersProtoBuf.DeleteUserResult;
import fctreddit.impl.grpc.generated_java.UsersProtoBuf.GetUserArgs;
import fctreddit.impl.grpc.generated_java.UsersProtoBuf.GetUserResult;
import fctreddit.impl.grpc.generated_java.UsersProtoBuf.GrpcUser;
import fctreddit.impl.grpc.generated_java.UsersProtoBuf.SearchUserArgs;
import fctreddit.impl.grpc.generated_java.UsersProtoBuf.UpdateUserArgs;
import fctreddit.impl.grpc.generated_java.UsersProtoBuf.UpdateUserResult;
import fctreddit.impl.grpc.util.DataModelAdaptor;
import fctreddit.impl.server.java.JavaUsers;
import io.grpc.BindableService;
import io.grpc.ServerServiceDefinition;
import io.grpc.stub.StreamObserver;

public class GrpcUsersServerStub implements UsersGrpc.AsyncService, BindableService {

	Users impl = new JavaUsers();
	private static Logger Log = Logger.getLogger(UsersServer.class.getName());

	@Override
	public final ServerServiceDefinition bindService() {
		return UsersGrpc.bindService(this);
	}

	@Override
	public void createUser(CreateUserArgs request, StreamObserver<CreateUserResult> responseObserver) {
		try {
			User user = DataModelAdaptor.GrpcUser_to_User(request.getUser());
			Result<String> res = impl.createUser(user);
			if (!res.isOK())
				responseObserver.onError(errorCodeToStatus(res.error()));
			else {
				CreateUserResult result = CreateUserResult.newBuilder().setUserId(res.value()).build();
				responseObserver.onNext(result);
				responseObserver.onCompleted();
			}
		} catch (Exception e) {
			// Log the exception
			Log.severe("Error creating user: " + e.getMessage());
			e.printStackTrace();
			
			// Return a more specific error if possible
			responseObserver.onError(io.grpc.Status.INTERNAL
				.withDescription("Internal server error: " + e.getMessage())
				.asException());
		}
	}

	@Override
	public void getUser(GetUserArgs request, StreamObserver<GetUserResult> responseObserver) {
		Result<User> res = impl.getUser(request.getUserId(), request.getPassword());
		if (!res.isOK())
			responseObserver.onError(errorCodeToStatus(res.error()));
		else {
			GetUserResult result = GetUserResult.newBuilder()
					.setUser(DataModelAdaptor.User_to_GrpcUser(res.value())).build();
			responseObserver.onNext(result);
			responseObserver.onCompleted();
		}
	}

	@Override
	public void updateUser(UpdateUserArgs request, StreamObserver<UpdateUserResult> responseObserver) {
		User user = DataModelAdaptor.GrpcUser_to_User(request.getUser());
		Result<User> res = impl.updateUser(request.getUserId(), request.getPassword(), user);
		if (!res.isOK())
			responseObserver.onError(errorCodeToStatus(res.error()));
		else {
			UpdateUserResult result = UpdateUserResult.newBuilder()
					.setUser(DataModelAdaptor.User_to_GrpcUser(res.value())).build();
			responseObserver.onNext(result);
			responseObserver.onCompleted();
		}
	}

	@Override
	public void deleteUser(DeleteUserArgs request, StreamObserver<DeleteUserResult> responseObserver) {
		Result<User> res = impl.deleteUser(request.getUserId(), request.getPassword());
		if (!res.isOK())
			responseObserver.onError(errorCodeToStatus(res.error()));
		else {
			DeleteUserResult result = DeleteUserResult.newBuilder()
					.setUser(DataModelAdaptor.User_to_GrpcUser(res.value())).build();
			responseObserver.onNext(result);
			responseObserver.onCompleted();
		}
	}

	@Override
	public void searchUsers(SearchUserArgs request, StreamObserver<GrpcUser> responseObserver) {
		Result<List<User>> res = impl.searchUsers(request.getPattern());

		if (!res.isOK())
			responseObserver.onError(errorCodeToStatus(res.error()));
		else {
			for (User u : res.value()) {
				responseObserver.onNext(DataModelAdaptor.User_to_GrpcUser(u));
			}
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
