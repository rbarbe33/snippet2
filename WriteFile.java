//Save the uploaded file into the specified client lib path

private String writeToClientLib(InputStream is, String fileName, String path, String mimetype)

{

try

{

    //Invoke the adaptTo method to create a Session

    ResourceResolver resourceResolver = resolverFactory.getAdministrativeResourceResolver(null);

    session = resourceResolver.adaptTo(Session.class);

    

    Node node = session.getNode(path);  //Get the client lib node in which to write the posted file

    javax.jcr.ValueFactory valueFactory = session.getValueFactory();            

    javax.jcr.Binary contentValue = valueFactory.createBinary(is);           

    Node fileNode = node.addNode(fileName, "nt:file");

    fileNode.addMixin("mix:referenceable");

    Node resNode = fileNode.addNode("jcr:content", "nt:resource");

    resNode.setProperty("jcr:mimeType", mimetype);

    resNode.setProperty("jcr:data", contentValue);

    Calendar lastModified = Calendar.getInstance();

    lastModified.setTimeInMillis(lastModified.getTimeInMillis());

    resNode.setProperty("jcr:lastModified", lastModified);

    session.save();           

    session.logout();

     

    // Return the path to the document that was stored in CRX.

    return fileNode.getPath();

}

