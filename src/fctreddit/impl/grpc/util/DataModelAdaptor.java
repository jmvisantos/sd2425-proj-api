package fctreddit.impl.grpc.util;

import fctreddit.api.User;
import fctreddit.impl.grpc.generated_java.UsersProtoBuf.GrpcUser;
import fctreddit.impl.grpc.generated_java.UsersProtoBuf.GrpcUser.Builder;

public class DataModelAdaptor {

	public static User GrpcUser_to_User( GrpcUser from )  {
		return new User( 
				from.getUserId(), 
				from.getFullName(),
				from.getEmail(), 
				from.getPassword(), 
				from.getAvatarUrl());	
	}

	public static GrpcUser User_to_GrpcUser( User from )  {
		Builder b = GrpcUser.newBuilder()
				.setUserId( from.getUserId())
				.setPassword( from.getPassword())
				.setEmail( from.getEmail())
				.setFullName( from.getFullName());
		
		if(from.getAvatarUrl() != null)
			b.setAvatarUrl( from.getAvatarUrl());
		
		return b.build();
	}

}
