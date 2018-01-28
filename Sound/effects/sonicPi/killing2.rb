
define :killing do
  sleep 0.2
  start_tone = 48
  volume = 0.3
  with_fx :reverb do
    with_synth :hoover do
      sample :bd_ada
    end
  end
  
  with_synth :fm do
    play start_tone, release:0.4
    sleep 0.5
    play start_tone-2, release:1
  end
  
  
end



killing
