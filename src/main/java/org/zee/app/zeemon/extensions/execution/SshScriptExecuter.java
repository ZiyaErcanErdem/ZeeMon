package org.zee.app.zeemon.extensions.execution;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.zee.app.zeemon.domain.Endpoint;
import org.zee.app.zeemon.domain.EndpointProperty;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

@Component
public class SshScriptExecuter {
	
	private boolean createRemoteScriptFile(ChannelSftp channelSftp, String scriptSource, String remoteDir, String remoteScriptName) throws SftpException {
	    // channelSftp.connect();
	  
	    // remoteDir = "remote_sftp_test/";
	    // remoteScriptName = "test_script.sh"
	    
	    InputStream source = IOUtils.toInputStream(scriptSource, Charset.forName("UTF-8"));

	  
	    channelSftp.put(source, remoteDir + remoteScriptName, ChannelSftp.OVERWRITE);
	  
	    // channelSftp.exit();
	    
	    return true;
	}
	
	private boolean removeRemoteScriptFile(ChannelSftp channelSftp, String remoteDir, String remoteScriptName) throws SftpException {
	    // channelSftp.connect();
	  
	    channelSftp.rm(remoteDir + remoteScriptName);
	  
	    // channelSftp.exit();
	    
	    return true;
	}
	
	private ChannelSftp createSftpChannel(Endpoint endpoint, List<EndpointProperty> props) throws JSchException {
		
		String sshPassword = "";
		
		if (null == props || props.isEmpty()) {
			return null;
		}
		
		Session jschSession =  this.createSession(endpoint, props);
		
		if (null == jschSession) {
			return null;
		}
		
		for(EndpointProperty p : props) {
			if ("sshPassword".equals(p.getPropKey())) {
				sshPassword = p.getPropValue();
			}
		}
	    jschSession.setPassword(sshPassword);
	    jschSession.connect();
	    return (ChannelSftp) jschSession.openChannel("sftp");
	}
	
	private Session createSession(Endpoint endpoint, List<EndpointProperty> props) throws JSchException {
		

		String sshRemoteHost = null;
		String sshUsername = null;
		int sshPort = 22;
		
		if (null == props || props.isEmpty()) {
			return null;
		}
		
		for(EndpointProperty p : props) {
			if ("sshRemoteHost".equals(p.getPropKey())) {
				sshRemoteHost = p.getPropValue();
			} else if ("sshUsername".equals(p.getPropKey())) {
				sshUsername = p.getPropValue();
			}else if ("sshPort".equals(p.getPropKey())) {
				String portStr = p.getPropValue();
				if (!StringUtils.isEmpty(portStr)) {
					sshPort = Integer.parseInt(portStr);
				}
			}
		}
		
		if (null == sshRemoteHost || null == sshUsername) {
			return null;
		}
		
	    JSch jsch = new JSch();
	    // jsch.setKnownHosts("/Users/john/.ssh/known_hosts");
	    Session jschSession = jsch.getSession(sshUsername, sshRemoteHost, sshPort);
	    return jschSession;

	}

}
