define :idjsfl do
  sample :ambi_dark_woosh, rate: 0.9, start:0.2, finish:0.8, pitch: 5, amp: 0.1
  sleep 0.25
end

start=45
speed = 0.15

define :wrong_kill do
  #with_fx :reverb do
  
  play start, release: 0.2
  sleep speed
  play start-1, release: 0.2
  sleep speed
  play start-2, release: 0.2
  sleep speed
  play start-4, release: 0.6
  #end
end



define :right_kill do
  start=50
  speed = 0.15
  #with_fx :reverb do
  play start, release: 0.2
  sleep speed
  play start+1, release: 0.2
  sleep speed
  play start+2, release: 0.2
  sleep speed
  play start+4, release: 0.6
  #end
end


wrong_kill
sleep 1
right_kill
