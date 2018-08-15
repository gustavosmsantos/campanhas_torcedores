curl -X POST --data-binary @- --dump - http://localhost:8529/_api/collection <<EOF
{ 
  "name" : "campanhas" 
}
EOF

curl -X POST --data-binary @- --dump - http://localhost:8529/_api/collection <<EOF
{
  "name" : "times"
}
EOF

curl -X POST --data-binary @- --dump - http://localhost:8529/_api/collection <<EOF
{
  "name" : "campanhas_times"
}
EOF
